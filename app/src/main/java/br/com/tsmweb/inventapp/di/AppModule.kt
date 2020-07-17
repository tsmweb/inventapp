package br.com.tsmweb.inventapp.di

import androidx.fragment.app.FragmentActivity
import br.com.tsmweb.data.db.database.AppDataBase
import br.com.tsmweb.data.db.locale.LocaleRoomDataSourceImpl
import br.com.tsmweb.data.locale.repository.LocaleRepositoryImpl
import br.com.tsmweb.data.locale.source.LocaleRoomDataSource
import br.com.tsmweb.domain.locale.interactor.ListLocalesUseCase
import br.com.tsmweb.domain.locale.interactor.RemoveLocaleUseCase
import br.com.tsmweb.domain.locale.interactor.SaveLocaleUseCase
import br.com.tsmweb.domain.locale.repository.LocaleRepository
import br.com.tsmweb.inventapp.router.AppRouter
import br.com.tsmweb.inventapp.common.Router
import br.com.tsmweb.inventapp.features.locale.LocaleFormViewModel
import br.com.tsmweb.inventapp.features.locale.LocaleListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { (activity: FragmentActivity) ->
        AppRouter(activity) as Router
    }

    /*-- Place --*/
    single {
        LocaleRoomDataSourceImpl(AppDataBase.getDatabase(context = get())) as LocaleRoomDataSource
    }

    factory {
        LocaleRepositoryImpl(localeRoomDataSource = get()) as LocaleRepository
    }

    factory {
        ListLocalesUseCase(repository = get())
    }

    factory {
        SaveLocaleUseCase(repository = get())
    }

    factory {
        RemoveLocaleUseCase(repository = get())
    }

    viewModel {
        LocaleListViewModel(
            listLocalesUseCase = get(),
            removeLocaleUseCase = get()
        )
    }

    viewModel {
        LocaleFormViewModel(
            saveLocaleUseCase = get()
        )
    }

}