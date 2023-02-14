package com.example.bankingapp.domain.use_cases

import com.example.bankingapp.data.model.Account
import com.example.bankingapp.data.model.AtmCard
import com.example.bankingapp.data.repository.CustomerRepository
import com.example.bankingapp.utils.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(private val customerRepository: CustomerRepository) {

    private fun calculateMaxLoanAmount(amount: Double) = amount.div(100) * 40

    operator fun invoke(
        name: String,
        accountType: AccountType,
        minimumAmount: String,
        loanType: LoanType,
        customerId: String
    ) = flow<String> {

        var accountNumber = ""

        val customer = customerRepository.getCustomer(customerId)

        when (accountType) {
            AccountType.LOAN_AC -> {
                val accountList = isCustomerHasSavingOrCurrentAcInTheBank(customerId = customerId)
                val amount = accountList.sumOf { it.minimumAmount.toDouble() }
                val maxLoanAmountGiven = calculateMaxLoanAmount(amount)
                when{
                    accountList.isEmpty()->{
                        emit("Customer has no account")
                    }
                    minimumAmount.toDouble()<500000->{
                        emit("Loan amount must be greater than 5 lakhs")
                    }
                    minimumAmount.toDouble() > maxLoanAmountGiven->{
                        emit("User has not fulfill the loan conditions")
                    }
                    !isValidAge(AccountType.LOAN_AC,customer.age.toInt()).first->{
                        val message=isValidAge(AccountType.LOAN_AC,customer.age.toInt()).second
                        emit(message)
                    }
                    else->{
                        accountNumber = generateAccountNumber()
                        val account = Account(
                            accountId = accountNumber,
                            customerId = customerId,
                            accountType = accountType,
                            noOfTransactionInCurrentMonth = 0,
                            minimumAmount = minimumAmount,
                            loanType = loanType
                        )
                        customerRepository.insertAccount(account = account)
                        emit("Loan pass Successfully")
                    }
                }
            }
            else -> {
                accountNumber = generateAccountNumber()
                when{
                    !isValidAge(accountType,customer.age.toInt()).first->{
                        val message=isValidAge(AccountType.LOAN_AC,customer.age.toInt()).second
                        emit(message)
                    }
                    else->{
                        val account = Account(
                            accountId = accountNumber,
                            customerId = customerId,
                            accountType = accountType,
                            noOfTransactionInCurrentMonth = 0,
                            minimumAmount = minimumAmount,
                            loanType = LoanType.NOTHING
                        )
                        customerRepository.insertAccount(account = account)
                        if(accountType == AccountType.SAVING_AC){
                            val atmCard = AtmCard(
                                accountNumber = accountNumber,
                                atmCardNumber = generateATMCardNumber(),
                                cvv = generateCVV(),
                                expiryDate = generateExpiryDateOfATMCard(),
                                atmHolderName = name
                            )
                            customerRepository.insertAtmCard(atmCard)
                        }
                        emit("Account Created Successfully")
                    }
                }

            }
        }
    }

    private fun generateAccountNumber() = generateRandomNumber(14)

    private fun generateATMCardNumber() = generateRandomNumber(16)

    private fun generateCVV() = generateRandomNumber(3)

    private fun generateExpiryDateOfATMCard(): String {
        val currentDate = getCurrentDate(Constant.DATE_FORMAT_SERVER)
        return addNoOfDaysInCurrentDate(currentDate, 365)
    }

    private fun isValidAge(accountType: AccountType, age: Int): Pair<Boolean, String> {
        return when (accountType) {
            AccountType.CURRENT_AC -> {
                Pair(age > 18, "Minimum amount amount to get loan is 5,000,00 and age is greater than 18")
            }
            AccountType.SAVING_AC -> {
                Pair(true, "")
            }
            AccountType.LOAN_AC -> {
                Pair(age > 25, "Minimum amount to open current a/c is 1,000,00  and age is greater than 25")
            }
        }
    }

    private suspend fun isCustomerHasSavingOrCurrentAcInTheBank(customerId: String) =
        customerRepository.getAccountList(customerId = customerId)

}