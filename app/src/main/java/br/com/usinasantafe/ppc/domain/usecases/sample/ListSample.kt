package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.presenter.model.SampleScreenModel
import javax.inject.Inject

interface ListSample {
    suspend operator fun invoke(): Result<List<SampleScreenModel>>
}

class IListSample @Inject constructor(
): ListSample {

    override suspend fun invoke(): Result<List<SampleScreenModel>> {
        TODO("Not yet implemented")
    }

}