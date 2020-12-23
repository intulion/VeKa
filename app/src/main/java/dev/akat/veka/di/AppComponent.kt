package dev.akat.veka.di

import dagger.Component
import dev.akat.veka.ui.MainActivity
import dev.akat.veka.ui.detail.PostDetailFragment
import dev.akat.veka.ui.favorites.FeedFavoritesFragment
import dev.akat.veka.ui.feed.FeedPagerFragment
import dev.akat.veka.ui.news.FeedNewsFragment
import dev.akat.veka.ui.post.CreatePostFragment
import dev.akat.veka.ui.profile.ProfileFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        MappersModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: FeedPagerFragment)

    fun inject(fragment: FeedNewsFragment)

    fun inject(fragment: FeedFavoritesFragment)

    fun inject(fragment: ProfileFragment)

    fun inject(fragment: PostDetailFragment)

    fun inject(fragment: CreatePostFragment)
}
