package com.mohitum.flagskotlinmvvm.models

/**
 * Data class to hold flags data with response message
 *
 * @param message response message to be bundled along with flags
 * @param flags flag list
 */
data class FlagsWrapper(@Message val message: String, val flags: List<Flag>?)