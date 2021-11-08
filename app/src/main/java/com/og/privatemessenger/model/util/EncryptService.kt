package com.og.privatemessenger.model.util

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

private const val ALGORITHM = "RSA"

class EncryptService {
    private val cipher: Cipher = Cipher.getInstance(ALGORITHM)

    fun createKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM)
        return keyPairGenerator.genKeyPair()
    }

    fun encryptMessage(message: ByteArray, publicKey: PublicKey): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(message)
    }

    fun decryptMessage(encryptedMessage: ByteArray, privateKey: PrivateKey): ByteArray {
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(encryptedMessage)
    }

}