package com.sujay.assignment

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.system.exitProcess

private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
private val accountService = AccountServiceImpl()

/**
 * Main method teakes in 3 command line arguments
 * 1. Account Id
 * 2. fromDate
 * 3. toDate
 */
fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    while (scanner.hasNext()) {
        addTransactions(scanner.nextLine())
    }
    if (args.size >= 3) {
        val accountId = args[0]
        val fromDate = args[1]
        val toDate = args[2]
        val result = accountService.getRelativeBalanceForPeriod(accountId, fromDate, toDate)
        println("Relative balance for the period is: -$%.2f".format(result.first))
        println("Number of transactions included is: ${result.second}")
    } else {
        println("Usage: program accountId fromDate toDate")
        exitProcess(1)
    }
}

/**
 * This method add transactionLine to the data store
 */
fun addTransactions(transactionLine: String) {

    val trans = transactionLine.split(",")
    val transactionType = when (trans[5]) {
        "PAYMENT" -> TransactionType.PAYMENT
        else -> TransactionType.REVERSAL
    }
    val transaction = Transaction(trans[0])
    transaction.accountId = trans[1].trim()
    transaction.toAccountId = trans[2].trim()
    transaction.transactionDate = LocalDateTime.parse(trans[3].trim(), dateTimeFormatter)
    transaction.amount = BigDecimal(trans[4].trim())
    transaction.transactionType = transactionType
    if (trans.size == 7) {
        transaction.refTransaction = trans[6].trim()
    }
    accountService.update(transaction)
}

