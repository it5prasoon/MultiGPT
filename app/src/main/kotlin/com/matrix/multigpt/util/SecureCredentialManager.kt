package com.matrix.multigpt.util

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Secure credential manager using Android Keystore for encryption
 * This replaces the vulnerable plaintext storage of AWS credentials
 */
@Singleton
class SecureCredentialManager @Inject constructor(
    private val context: Context
) {
    private companion object {
        const val KEYSTORE_ALIAS = "MultiGPTCredentialKey"
        const val ENCRYPTED_PREFS_NAME = "secure_credentials"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val IV_LENGTH = 12
    }

    private val encryptedPrefs by lazy {
        try {
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            EncryptedSharedPreferences.create(
                ENCRYPTED_PREFS_NAME,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            throw SecurityException("Failed to initialize secure credential storage", e)
        }
    }

    /**
     * Securely stores credentials using encryption
     */
    fun storeCredentials(key: String, credentials: String): Boolean {
        return try {
            if (credentials.isBlank()) {
                // Clear credentials
                encryptedPrefs.edit().remove(key).apply()
                true
            } else {
                // Validate credential format before storing
                validateCredentialFormat(credentials)
                encryptedPrefs.edit().putString(key, credentials).apply()
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Securely retrieves credentials with decryption
     */
    fun retrieveCredentials(key: String): String? {
        return try {
            encryptedPrefs.getString(key, null)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Securely deletes credentials
     */
    fun deleteCredentials(key: String): Boolean {
        return try {
            encryptedPrefs.edit().remove(key).apply()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Validates credential format to prevent malformed data storage
     */
    private fun validateCredentialFormat(credentials: String) {
        try {
            // Attempt to parse as JSON to validate format
            Json.decodeFromString<Map<String, String>>(credentials)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid credential format", e)
        }
    }

    /**
     * Checks if credentials exist for a given key
     */
    fun hasCredentials(key: String): Boolean {
        return encryptedPrefs.contains(key)
    }

    /**
     * Clears all stored credentials (for security reset)
     */
    fun clearAllCredentials(): Boolean {
        return try {
            encryptedPrefs.edit().clear().apply()
            true
        } catch (e: Exception) {
            false
        }
    }
}
