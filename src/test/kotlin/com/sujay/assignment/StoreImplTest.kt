package com.sujay.assignment

import org.junit.jupiter.api.Assertions.assertEquals
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class StoreImplTest {
    private val dataStore: Store<String, Set<Transaction>> = StoreImpl()

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {
    }

    @org.junit.jupiter.api.Test
    fun save() {
        val transaction1 = Transaction("TX10001")
        transaction1.accountId = "ACC334455"
        transaction1.toAccountId = "ACC778899"
        transaction1.transactionDate = LocalDateTime.parse("20/10/2018 12:47:55", dateTimeFormatter)
        transaction1.amount = BigDecimal("25.00")
        transaction1.transactionType = TransactionType.PAYMENT
        val account = Account("ACC334455")
        account.transactions = listOf(transaction1)
        this.dataStore.save(account.accountId, setOf(transaction1))
    }

    @org.junit.jupiter.api.Test
    fun get() {
        val transaction1 = Transaction("TX10001")
        transaction1.accountId = "ACC334455"
        transaction1.toAccountId = "ACC778899"
        transaction1.transactionDate = LocalDateTime.parse("20/10/2018 12:47:55", dateTimeFormatter)
        transaction1.amount = BigDecimal("25.00")
        transaction1.transactionType = TransactionType.PAYMENT
        val account = Account("ACC334455")
        account.transactions = listOf(transaction1)
        val transaction = dataStore.get("ACC334455")
        this.dataStore.save(account.accountId, setOf(transaction1))
        if (transaction != null) {
            assertEquals(1, transaction.size)
        }
    }
}