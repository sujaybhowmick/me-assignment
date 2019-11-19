package com.sujay.assignment

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class AccountServiceImplTest {

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    lateinit var accountService: AccountService

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        this.accountService = AccountServiceImpl()
        File(this.javaClass.classLoader.getResource("data.csv").file).useLines { lines ->
            lines.map { line -> line.split(",") }.toList().forEach { trans ->
                val transactionType = when (trans[5]) {
                    "PAYMENT" -> TransactionType.PAYMENT
                    else -> TransactionType.REVERSAL
                }
                val transaction = Transaction(trans[0])
                transaction.accountId = trans[1]
                transaction.toAccountId = trans[2]
                transaction.transactionDate = LocalDateTime.parse(trans[3], dateTimeFormatter)
                transaction.amount = BigDecimal(trans[4])
                transaction.transactionType = transactionType
                if (trans.size == 7) {
                    transaction.refTransaction = trans[6]
                }
                this.accountService.update(transaction)
            }
        }
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {
    }

    @Test
    fun update() {
        val transaction1 = Transaction("TX10006")
        transaction1.accountId = "ACC778899"
        transaction1.toAccountId = "ACC76734"
        transaction1.transactionDate = LocalDateTime.parse("21/10/2018 12:47:55", dateTimeFormatter)
        transaction1.amount = BigDecimal("10.50")
        transaction1.transactionType = TransactionType.PAYMENT
        this.accountService.update(transaction1)
        val transactions = this.accountService.get("ACC778899")
        assertEquals(1, transactions?.size)
    }

    @Test
    fun getRelativeBalanceForPeriod() {
        val result = this.accountService.getRelativeBalanceForPeriod(
            "ACC334455",
            "20/10/2018 12:00:00", "20/10/2018 19:00:00"
        )
        assertEquals(25.00, result.first)
        assertEquals(1, result.second)
    }
}