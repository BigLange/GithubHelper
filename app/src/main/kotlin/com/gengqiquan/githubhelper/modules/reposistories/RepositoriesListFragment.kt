package com.gengqiquan.githubhelper.modules.reposistories

import android.os.Bundle
import android.view.View
import com.gengqiquan.adapter.adapter.RBAdapter
import com.gengqiquan.adapter.interfaces.Holder
import com.gengqiquan.githubhelper.R
import com.gengqiquan.githubhelper.base.MVPFragment
import com.gengqiquan.githubhelper.data.Repo
import com.gengqiquan.githubhelper.expansions.BindViewLifeAndSchedulers
import com.gengqiquan.githubhelper.provides.GithubService
import kotlinx.android.synthetic.main.fragment_repositories_list.*


class RepositoriesListFragment : MVPFragment() {

    var page = 1
    lateinit var userID: String
    lateinit var type: String
    override fun getLayoutID() = R.layout.fragment_repositories_list

    override fun inject() {

    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        userID = arguments.getString("userLogin")
        type = arguments.getString("type")

        refresh_layout.adapter(RBAdapter<Repo>(mContext)
                .bindViewData(this::bindViewAndData)
                .layout(R.layout.item_user_repositorie_list))
                .loadMore { load(false) }
                .refresh { load(true) }
                .doRefresh()
    }

    override fun initData() {
    }

    fun bindViewAndData(holder: Holder, item: Repo) {
        holder.setText(R.id.name, item.name)
        holder.setText(R.id.desc, item.description)
        holder.setText(R.id.language, item.language)
        if (item.stargazersCount > 0) {
            holder.getView<View>(R.id.star).visibility = View.VISIBLE
        } else {
            holder.getView<View>(R.id.star).visibility = View.GONE
        }
        if (item.forksCount > 0) {
            holder.getView<View>(R.id.fork).visibility = View.VISIBLE
        } else {
            holder.getView<View>(R.id.fork).visibility = View.GONE
        }
        holder.setText(R.id.star, item.stargazersCount.toString())
        holder.setText(R.id.fork, item.forksCount.toString())
    }

    fun load(refresh: Boolean) {
        if (refresh) page = 1
        retrofit.create(GithubService::class.java).getRepositoriesList(userID, type, page)
                .BindViewLifeAndSchedulers(this)
                .subscribe({
                    if (refresh) {
                        refresh_layout.refreshComplete(it)
                    } else {
                        refresh_layout.loadMoreComplete(it)
                    }
                    page++

                }) { e ->
                    e.printStackTrace()
                    refresh_layout.loadFailure()
                }
    }


}
