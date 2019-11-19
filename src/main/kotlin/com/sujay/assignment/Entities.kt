package com.sujay.assignment

import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * This class represents an Account
 * @property accountId Unique identifier of the account
 * @property transactions Set of transactions related to the account
 */
data class Account(val accountId: String) {
    lateinit var transactions: List<Transaction>
}

/**
 * This class represents a transaction related to an account
 * @property transactionId Unique identifier of the transaction
 * @property accountId Account id related to the transaction
 * @property toAccountId another Account id related to the transaction
 * @property transactionDate Date and time of the transaction
 * @property amount Amount of the transaction
 * @property transactionType Transaction type of the transaction (PAYMENT, REVERSAL)
 * @property refTransaction Reversal transaction for the transaction id represented by the PAYMENT transaction type
 */
data class Transaction(val transactionId: String) {
    lateinit var accountId: String
    lateinit var toAccountId: String
    lateinit var transactionDate: LocalDateTime
    lateinit var amount: BigDecimal
    lateinit var transactionType: TransactionType
    var refTransaction: String? = null
}

/**
 * Enum representing the transaction types
 * @property type Type of the transaction (PAYMENT, REVERSAL)
 */
enum class TransactionType(val type: String) {
    PAYMENT("PAYMENT"),
    REVERSAL("REVERSAL")
}

