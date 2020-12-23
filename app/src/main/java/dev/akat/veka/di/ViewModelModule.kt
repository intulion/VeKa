package dev.akat.veka.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.akat.veka.ui.detail.PostDetailViewModel
import dev.akat.veka.ui.favorites.FeedFavoritesViewModel
import dev.akat.veka.ui.feed.FeedPagerViewModel
import dev.akat.veka.ui.news.FeedNewsViewModel
import dev.akat.veka.ui.post.CreatePostViewModel
import dev.akat.veka.ui.profile.ProfileViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeedPagerViewModel::class)
    abstract fun bindFeedPagerViewModel(viewModel: FeedPagerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedNewsViewModel::class)
    abstract fun bindFeedNewsViewModel(viewModel: FeedNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedFavoritesViewModel::class)
    abstract fun bindFeedFavoritesViewModel(viewModel: FeedFavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreatePostViewModel::class)
    abstract fun bindCreatePostViewModel(viewModel: CreatePostViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    abstract fun bindPostDetailViewModel(viewModel: PostDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
