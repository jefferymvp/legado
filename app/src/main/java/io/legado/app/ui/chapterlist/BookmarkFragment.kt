package io.legado.app.ui.chapterlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import io.legado.app.App
import io.legado.app.R
import io.legado.app.base.VMBaseFragment
import io.legado.app.data.entities.Bookmark
import io.legado.app.lib.theme.ATH
import io.legado.app.utils.getViewModelOfActivity
import kotlinx.android.synthetic.main.fragment_bookmark.*

class BookmarkFragment : VMBaseFragment<ChapterListViewModel>(R.layout.fragment_bookmark) {
    override val viewModel: ChapterListViewModel
        get() = getViewModelOfActivity(ChapterListViewModel::class.java)

    private lateinit var adapter: BookmarkAdapter
    private var bookmarkLiveData: LiveData<PagedList<Bookmark>>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        ATH.applyEdgeEffectColor(rv_list)
        adapter = BookmarkAdapter()
        rv_list.layoutManager = LinearLayoutManager(requireContext())
        rv_list.adapter = adapter
    }

    private fun initData() {
        bookmarkLiveData?.removeObservers(viewLifecycleOwner)
        bookmarkLiveData = LivePagedListBuilder(App.db.bookmarkDao().observeByBook(""), 20).build()
        bookmarkLiveData?.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
    }
}