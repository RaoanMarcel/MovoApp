package Movo.demo.Config

import io.github.cdimascio.dotenv.Dotenv

object DotenvConfig {
    val dotenv: Dotenv = Dotenv.configure()
        .ignoreIfMissing()
        .load()
}
