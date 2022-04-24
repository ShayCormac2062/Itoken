package com.example.itoken.features.trades.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.App
import com.example.itoken.common.db.model.User
import com.example.itoken.databinding.FragmentMembersBinding
import com.example.itoken.databinding.ViewChooseMarkBinding
import com.example.itoken.databinding.ViewNotificationBinding
import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.TradeModel
import com.example.itoken.features.trades.presentation.adapter.MembersAdapter
import com.example.itoken.features.trades.presentation.viewmodel.TransactionViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MembersFragment : BottomSheetDialogFragment() {

    private var binding: FragmentMembersBinding? = null
    private var members: ArrayList<Auctioneer>? = null
    private var user: User? = null
    private var trade: TradeModel? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val transactionViewModel: TransactionViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMembersBinding.inflate(layoutInflater)
        members = arguments?.get("candidates") as ArrayList<Auctioneer>?
        user = arguments?.get("user") as User?
        trade = arguments?.get("trade") as TradeModel?
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            if (user != null && user?.nickname != trade?.author) {
                btnGiveAMark.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        createChooseMarkDialog()
                    }
                }
            }
            rvMembers.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.VERTICAL
                }
                adapter = MembersAdapter(members, trade?.author == user?.nickname).apply {
                    onClick = {
                        createSendTokenDialog(it)
                    }
                }
            }
        }
    }

    private fun createSendTokenDialog(auctioneer: Auctioneer?) {
        val bindingOfDialog: ViewNotificationBinding
        val alerts = AlertDialog.Builder(context).apply {
            bindingOfDialog = ViewNotificationBinding.inflate(LayoutInflater.from(context))
            setView(bindingOfDialog.root)
        }.show()
        with(bindingOfDialog) {
            btnNo.setOnClickListener {
                alerts.dismiss()
            }
            btnYes.setOnClickListener {
                lifecycleScope.launch {
                    trade?.token?.let { lot ->
                        with(transactionViewModel) {
                            sendTokenToUser(
                                auctioneer?.stringId,
                                lot
                            )
                            closeTrade(lot.tokenId)
                        }
                    }

                }
                makeToast("Ваш токен был продан пользователю \"${auctioneer?.name}\"")
                alerts.dismiss()
                dismiss()
            }
            tvNotification.text =
                "Вы точно хотите продать токен пользователю \"${auctioneer?.name}\""
        }
    }

    private fun createChooseMarkDialog() {
        val bindingOfDialog: ViewChooseMarkBinding
        val alerts = AlertDialog.Builder(context).apply {
            bindingOfDialog = ViewChooseMarkBinding.inflate(LayoutInflater.from(context))
            setView(bindingOfDialog.root)
        }.show()
        with(bindingOfDialog) {
            btnNo.setOnClickListener {
                alerts.dismiss()
            }
            btnYes.setOnClickListener {
                if (
                    tietMark.text.toString() != "" &&
                    tietMark.text.toString().toDouble() <= user?.balance.toString().toDouble() &&
                    tietMark.text.toString().toDouble() >= trade?.price.toString().toDouble()
                ) {
                    lifecycleScope.launch {
                        val newAuctioneer = Auctioneer(
                            user?.stringId,
                            user?.nickname,
                            tietMark.text.toString().toLong(),
                            user?.imageUrl
                        )
                        val checkedAuctioneer = try {
                            members?.first {
                                it.name == newAuctioneer.name &&
                                        it.stringId == newAuctioneer.stringId
                            }
                        } catch (ex: Exception)  {
                            null
                        }
                        if (checkedAuctioneer != null) {
                            members?.remove(checkedAuctioneer)
                        }
                        members?.add(newAuctioneer)
                        transactionViewModel.changeMembersList(trade?.token?.tokenId, members)
                        makeToast("Ваша ставка принята!")
                        dismiss()
                    }
                    alerts.dismiss()
                } else makeToast("Введите сумму, которая есть на вашем счету!")
            }
        }
    }

    private fun makeToast(text: String) = Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()


        //TODO не работает подача ставки. Исправить
}