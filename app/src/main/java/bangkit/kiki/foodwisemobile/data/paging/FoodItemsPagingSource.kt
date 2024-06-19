package bangkit.kiki.foodwisemobile.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import bangkit.kiki.foodwisemobile.data.api.ApiService
import bangkit.kiki.foodwisemobile.data.model.FoodItemModel

class FoodItemsPagingSource(
    private val apiService: ApiService,
    private val token: String,
    private val type: Int
) : PagingSource<Int, FoodItemModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FoodItemModel> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getItems(page, params.loadSize, type, token)
            LoadResult.Page(
                data = response.foods,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.foods.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FoodItemModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
