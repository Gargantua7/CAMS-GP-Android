package com.gargantua7.cams.gp.android.logic.exception

/**
 * @author Gargantua7
 */
sealed class ResultException(val code: Int, val msg: String, val info: String) :
    RuntimeException("[$code: $msg] -> $info") {
    override fun toString(): String {
        return "[$code: $msg] -> $info"
    }

    companion object {

        fun build(code: Int, msg: String, info: String): ResultException {
            return when (code) {
                400 -> BadRequestException(msg, info)
                401 -> AuthorizedException(msg, info)
                403 -> ForbiddenException(msg, info)
                404 -> NotFoundException(msg, info)
                else -> throw UnsupportedOperationException("Unknown Result Exception")
            }
        }

    }

}

class BadRequestException(msg: String, info: String) : ResultException(400, msg, info)

class AuthorizedException(msg: String, info: String) : ResultException(401, msg, info)

class ForbiddenException(msg: String, info: String) : ResultException(403, msg, info)

class NotFoundException(msg: String, info: String) : ResultException(404, msg, info)
