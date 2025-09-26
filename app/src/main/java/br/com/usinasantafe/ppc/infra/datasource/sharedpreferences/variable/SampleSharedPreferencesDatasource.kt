package br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable

import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.SampleSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.Field

interface SampleSharedPreferencesDatasource {
    suspend fun setValue(
        field: Field,
        value: Double
    ): Result<Boolean>
    suspend fun clean(): Result<Boolean>
    suspend fun getTare(): Result<Double>
    suspend fun setObs(
        stone: Boolean,
        treeStump: Boolean,
        weed: Boolean,
        anthill: Boolean
    ): Result<Boolean>
    suspend fun setSubObs(
        guineaGrass: Boolean,
        castorOilPlant: Boolean,
        signalGrass: Boolean,
        mucuna: Boolean,
        silkGrass: Boolean
    ): Result<Boolean>
    suspend fun get(): Result<SampleSharedPreferencesModel>

}