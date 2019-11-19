package com.sujay.assignment

interface Store<K, E> {
    fun save(key: K, element: E)

    fun get(key: K): E?
}

/**
 * Represents data store for storing the transactions
 */
class StoreImpl : Store<String, Set<Transaction>> {

    private var data: HashMap<String, Set<Transaction>> = HashMap()

    /**
     * Saves the element for the account id
     * @param key Account Id
     * @param element list of element
     */
    override fun save(key: String, element: Set<Transaction>) {
        data[key] = element
    }

    /**
     * Get the transaction associated with account id
     * @param key Account Id
     */
    override fun get(key: String): Set<Transaction>? {
        return data[key]
    }
}