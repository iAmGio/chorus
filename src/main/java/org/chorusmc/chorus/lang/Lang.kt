package org.chorusmc.chorus.lang

/**
 * Used for internazionalization via resource bundles
 * @author Giorgio Garofalo
 */
enum class Lang(val tag: String, private val commonName: String) {

    ENGLISH("en", "English"),
    ITALIAN("it", "Italiano"),
    GERMAN("de", "Deutsch"),
    SCHINESE("zhCN", "简体中文");

    companion object {
        @JvmStatic fun fromCommonName(name: String) = values().first {it.commonName.equals(name, true)}
    }
}
