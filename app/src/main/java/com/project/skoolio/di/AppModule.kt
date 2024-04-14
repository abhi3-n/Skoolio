package com.project.skoolio.di

import com.project.skoolio.network.Backend
import com.project.skoolio.repositories.AttendanceRepository
import com.project.skoolio.repositories.FeePaymentRepository
import com.project.skoolio.repositories.IssueRepository
import com.project.skoolio.repositories.OtpValidationRepository
import com.project.skoolio.repositories.PendingApprovalsRepository
import com.project.skoolio.repositories.RegistrationScreenRepository
import com.project.skoolio.repositories.SchoolInformationRepository
import com.project.skoolio.utils.Constants
import com.project.skoolio.viewModels.AttendanceViewModel
import com.project.skoolio.viewModels.FeePaymentViewModel
import com.project.skoolio.viewModels.IssueViewModel
import com.project.skoolio.viewModels.LoginViewModel
import com.project.skoolio.viewModels.OtpValidationViewModel
import com.project.skoolio.viewModels.PendingApprovalsViewModel
import com.project.skoolio.viewModels.RegistrationScreenViewModel
import com.project.skoolio.viewModels.SchoolInformationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideBackend(): Backend {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Backend::class.java)
    }


    //Repositories
    @Provides
    @Singleton
    fun provideIssuesRepository(backend: Backend) = IssueRepository(backend)
    @Provides
    @Singleton
    fun provideRegistrationScreenRepository(backend: Backend) = RegistrationScreenRepository(backend)
    @Provides
    @Singleton
    fun provideOtpValidationRepository(backend: Backend) = OtpValidationRepository(backend)
    @Provides
    @Singleton
    fun providePendingApprovalsRepository(backend: Backend) = PendingApprovalsRepository(backend)
    @Provides
    @Singleton
    fun provideAttendanceRepository(backend: Backend) = AttendanceRepository(backend)
    @Provides
    @Singleton
    fun provideSchoolInformationRepository(backend: Backend) = SchoolInformationRepository(backend)
    @Provides
    @Singleton
    fun provideFeePaymentRepository(backend: Backend) = FeePaymentRepository(backend)

    //ViewModels
    @Provides
    @Singleton
    fun provideLoginViewModel(backend: Backend) = LoginViewModel(backend)
    @Provides
    @Singleton
    fun provideRegistrationScreenViewModel(registrationScreenRepository: RegistrationScreenRepository, backend: Backend)
    = RegistrationScreenViewModel(registrationScreenRepository, backend)

    @Provides
    @Singleton
    fun provideOtpValidationViewModel(otpValidationRepository: OtpValidationRepository) = OtpValidationViewModel(otpValidationRepository)

    @Provides
    @Singleton
    fun providePendingApprovalsViewModel(pendingApprovalsRepository: PendingApprovalsRepository): PendingApprovalsViewModel = PendingApprovalsViewModel(pendingApprovalsRepository)

    @Provides
    @Singleton
    fun provideAttendanceViewModel(attendanceRepository: AttendanceRepository): AttendanceViewModel = AttendanceViewModel(attendanceRepository)

    @Provides
    @Singleton
    fun provideSchoolInformationViewModel(schoolInformationRepository: SchoolInformationRepository): SchoolInformationViewModel = SchoolInformationViewModel(schoolInformationRepository)

    @Provides
    @Singleton
    fun provideIssuesViewModel(issueRepository: IssueRepository, backend: Backend): IssueViewModel = IssueViewModel(issueRepository, backend)

    @Provides
    @Singleton
    fun provideFeePaymentViewModel(feePaymentRepository: FeePaymentRepository): FeePaymentViewModel = FeePaymentViewModel(feePaymentRepository)
}