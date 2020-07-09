package br.com.tsmweb.inventapp.di

import androidx.fragment.app.FragmentActivity
import br.com.tsmweb.data.db.database.AppDataBase
import br.com.tsmweb.data.db.place.PlaceRoomDataSourceImpl
import br.com.tsmweb.data.place.repository.PlaceRepositoryImpl
import br.com.tsmweb.data.place.source.PlaceRoomDataSource
import br.com.tsmweb.domain.place.interactor.ListPlacesUseCase
import br.com.tsmweb.domain.place.repository.PlaceRepository
import br.com.tsmweb.inventapp.router.AppRouter
import br.com.tsmweb.inventapp.common.Router
import br.com.tsmweb.inventapp.features.place.PlaceListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { (activity: FragmentActivity) ->
        AppRouter(activity) as Router
    }

    /*-- Place --*/
    single {
        PlaceRoomDataSourceImpl(AppDataBase.getDatabase(context = get())) as PlaceRoomDataSource
    }

    factory {
        PlaceRepositoryImpl(placeRoomDataSource = get()) as PlaceRepository
    }

    factory {
        ListPlacesUseCase(repository = get())
    }

    viewModel {
        PlaceListViewModel(
            listPlacesUseCase = get()
        )
    }

}