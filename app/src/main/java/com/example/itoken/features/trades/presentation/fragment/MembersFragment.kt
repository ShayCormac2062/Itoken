package com.example.itoken.features.trades.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.App
import com.example.itoken.R
import com.example.itoken.common.db.model.User
import com.example.itoken.databinding.FragmentMembersBinding
import com.example.itoken.databinding.ViewChooseMarkBinding
import com.example.itoken.features.trades.domain.model.Auctioneer
import com.example.itoken.features.trades.domain.model.TradeModel
import com.example.itoken.features.trades.presentation.adapter.MembersAdapter
import com.example.itoken.features.trades.presentation.viewmodel.TransactionViewModel
import com.example.itoken.utils.CommonUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class MembersFragment : BottomSheetDialogFragment() {

    private var binding: FragmentMembersBinding? = null
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
                adapter = MembersAdapter(trade?.candidates, trade?.author == user?.nickname).apply {
                    onClick = { auctioneer ->
                        CommonUtils.showDialog(
                            context,
                            String.format(
                                getString(R.string.send_token_to_user_question),
                                auctioneer?.name
                            ),
                            onClickEvent = {
                                trade?.token?.let { lot ->
                                    TradingFragment.auctioneer = auctioneer
                                    TradingFragment.lot = lot
                                    CommonUtils.makeToast(
                                        context,
                                        String.format(
                                            getString(R.string.send_token_to_user_complete),
                                            auctioneer?.name
                                        )
                                    )
                                    dismiss()
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun createChooseMarkDialog() {
        val bindingOfDialog: ViewChooseMarkBinding
        val alerts = AlertDialog.Builder(context).apply {
            bindingOfDialog = ViewChooseMarkBinding.inflate(LayoutInflater.from(context))
            setView(bindingOfDialog.root)
        }.show()
        with(bindingOfDialog) {
            tvBalance.text = String.format(
                getString(R.string.current_balance),
                user?.balance?.toInt()
            )
            btnNo.setOnClickListener {
                alerts.dismiss()
            }
            btnYes.setOnClickListener {
                if (
                    tietMark.text.toString() != "" &&
                    tietMark.text.toString().toDouble() <= user?.balance.toString().toDouble() &&
                    tietMark.text.toString().toDouble() >= trade?.price.toString().toDouble()
                ) {
                    val newAuctioneer = Auctioneer(
                        user?.stringId,
                        user?.nickname,
                        tietMark.text.toString().toLong(),
                        user?.imageUrl
                    )
                    transactionViewModel.changeMembersList(
                        trade?.token?.address,
                        newAuctioneer
                    )
                    CommonUtils.makeToast(
                        context,
                        getString(R.string.mark_was_given)
                    )
                    alerts.dismiss()
                    dismiss()
                } else CommonUtils.makeToast(
                    context,
                    getString(R.string.bad_mark)
                )
            }
        }
    }

}
