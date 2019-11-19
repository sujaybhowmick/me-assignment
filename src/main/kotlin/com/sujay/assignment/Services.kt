package com.sujay.assignment

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface AccountService {

    fun get(accountId: String): Set<Transaction>?

    fun update(transaction: Transaction)

    fun getRelativeBalanceForPeriod(accountId: String, fromDate: String, toDate: String): Pair<Double?, Int?>
}

/**
 * Represents Account Service to add, get and get relative account balances
 */
class AccountServiceImpl : AccountService {

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    private val dataStore: Store<String, Set<Transaction>> = StoreImpl()

    /**
     * Adds and updates the account associated with the transaction
     * @param transaction Transaction to be updated
     */
    override fun update(transaction: Transaction) {
        synchronized(dataStore) {
            val transactions = this.dataStore.get(transaction.accountId)
            if (transactions != null) {
                val mutableSetOfTransactions = mutableSetOf<Transaction>()
                mutableSetOfTransactions.addAll(transactions)
                mutableSetOfTransactions.add(transaction)
                this.dataStore.save(transaction.accountId, mutableSetOfTransactions.toSet())
            } else {
                this.dataStore.save(transaction.accountId, setOf(transaction))
            }
        }
    }

    /**
     *  Gets the relative balance for a period (from date and to date)
     *  @param accountId Account id for which we need the relative balance
     *  @param fromDate From date of the period
     *  @param toDate To date of the period
     */
    override fun getRelativeBalanceForPeriod(accountId: String, fromDate: String, toDate: String): Pair<Double?, Int?> {
        val transactions = dataStore.get(accountId)
        val fromLocalDateTime = LocalDateTime.parse(fromDate, dateTimeFormatter)
        val toLocalDateTime = LocalDateTime.parse(toDate, dateTimeFormatter)
        val filteredTransactions = transactions?.filter { it ->
            (it.transactionDate >= fromLocalDateTime && it.transactionDate <= toLocalDateTime) || it.transactionType == TransactionType.REVERSAL
        }
        var balance = filteredTransactions?.sumByDouble { selector ->
            when (selector.transactionType) {
                TransactionType.REVERSAL -> -selector.amount.toDouble()
                TransactionType.PAYMENT -> selector.amount.toDouble()
            }
        }
        var transactionCount = filteredTransactions?.sumBy { it ->
            when (it.transactionType) {
                TransactionType.REVERSAL -> -1
                TransactionType.PAYMENT -> 1
            }
        }
        return if( balance == null && transactionCount == null) {
            Pair(0.00, 0)
        }else
            Pair(balance, transactionCount)
    }

    /**
     * Gets the transactions for the account id
     * @param accountId Account Id needed for the transaction
     */
    override fun get(accountId: String): Set<Transaction>? {
        return this.dataStore.get(accountId)
    }
}