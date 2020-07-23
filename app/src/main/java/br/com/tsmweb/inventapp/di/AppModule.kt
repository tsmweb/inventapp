package br.com.tsmweb.inventapp.di

import androidx.navigation.NavController
import br.com.tsmweb.data.db.database.AppDataBase
import br.com.tsmweb.data.db.locale.LocaleRoomDataSourceImpl
import br.com.tsmweb.data.db.patrimony.PatrimonyRoomDataSourceImpl
import br.com.tsmweb.data.locale.repository.LocaleRepositoryImpl
import br.com.tsmweb.data.locale.source.LocaleRoomDataSource
import br.com.tsmweb.data.patrimony.repository.PatrimonyRepositoryImpl
import br.com.tsmweb.data.patrimony.source.PatrimonyRoomDataSource
import br.com.tsmweb.domain.locale.interactor.ListLocalesUseCase
import br.com.tsmweb.domain.locale.interactor.RemoveLocaleUseCase
import br.com.tsmweb.domain.locale.interactor.SaveLocaleUseCase
import br.com.tsmweb.domain.locale.repository.LocaleRepository
import br.com.tsmweb.domain.patrimony.interactor.ListPatrimonyUseCase
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import br.com.tsmweb.inventapp.router.AppRouter
import br.com.tsmweb.inventapp.common.Router
import br.com.tsmweb.inventapp.features.locale.LocaleFormViewModel
import br.com.tsmweb.inventapp.features.locale.LocaleListViewModel
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { (navController: NavController) ->
        AppRouter(navController) as Router
    }

    /*-- Locale --*/
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

    /*-- Patrimony --*/
    single {
        PatrimonyRoomDataSourceImpl(AppDataBase.getDatabase(context = get())) as PatrimonyRoomDataSource
    }

    factory {
        PatrimonyRepositoryImpl(patrimonyRoomDataSource = get()) as PatrimonyRepository
    }

    factory {
        ListPatrimonyUseCase(repository = get())
    }

    viewModel { (localeId: String) ->
        PatrimonyListViewModel(
            localeId,
            listPatrimonyUseCase = get()
        )
    }

    /*-- Inventory --*/

}