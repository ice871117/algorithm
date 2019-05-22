class Trie {

    /** Initialize your data structure here. */
    var value: String? = null
    private val children = Array<Trie?>(26) { null }

    /** Inserts a word into the trie. */
    fun insert(word: String) {
        var curr = this
        for (c in word.toCharArray()) {
            if (curr.children[c - 'a'] == null) {
                curr.children[c - 'a'] = Trie()
            }
            curr = curr.children[c - 'a']!!
        }
        curr.value = word
    }

    /** Returns if the word is in the trie. */
    fun search(word: String): Boolean {
        var curr = this
        for (c in word.toCharArray()) {
            if (curr.children[c - 'a'] == null) {
                return false
            }
            curr = curr.children[c - 'a']!!
        }
        return curr.value != null
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    fun startsWith(prefix: String): Boolean {
        var curr = this
        for (c in prefix.toCharArray()) {
            if (curr.children[c - 'a'] == null) {
                return false
            }
            curr = curr.children[c - 'a']!!
        }
        return true
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val root = Trie()
            root.insert("apple")
            println("search(apple) ${root.search("apple")}")
            println("search(app) ${root.search("app")}")
            println("startsWith(apple) ${root.startsWith("app")}")
        }
    }

}