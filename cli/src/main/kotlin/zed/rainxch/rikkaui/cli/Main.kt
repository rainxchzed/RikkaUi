package zed.rainxch.rikkaui.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import zed.rainxch.rikkaui.cli.commands.AddCommand
import zed.rainxch.rikkaui.cli.commands.InitCommand
import zed.rainxch.rikkaui.cli.commands.ListCommand

class RikkaCli : CliktCommand(name = "rikkaui") {
    override fun help(context: Context) =
        "RikkaUI — Add components to your Compose Multiplatform project"

    override fun run() = Unit
}

fun main(args: Array<String>) =
    RikkaCli()
        .subcommands(InitCommand(), AddCommand(), ListCommand())
        .main(args)
