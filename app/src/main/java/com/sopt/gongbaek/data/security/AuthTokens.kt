package com.sopt.gongbaek.data.security

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class AuthTokens(
    val signUpToken: String,
    val accessToken: String,
    val refreshToken: String
)

object AuthTokensSerializer : Serializer<AuthTokens> {
    override val defaultValue: AuthTokens = AuthTokens("", "", "")

    override suspend fun readFrom(input: InputStream): AuthTokens {
        val decrypted = CryptoManager.decrypt(input.readBytes())
        return Json.decodeFromString(decrypted.decodeToString())
    }

    override suspend fun writeTo(t: AuthTokens, output: OutputStream) {
        val plain = Json.encodeToString(t).encodeToByteArray()
        val encrypted = CryptoManager.encrypt(plain)
        withContext(Dispatchers.IO) {
            output.write(encrypted)
        }
    }
}
