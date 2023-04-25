import utils.ScannerInput

const val ansiReset = "\u001B[0m"
const val ansiRed = "\u001B[31m"
const val ansiGreen = "\u001B[32m"
const val ansiYellow = "\u001B[33m"
const val ansiBlue = "\u001B[34m"
const val ansiCyan = "\u001B[36m"

fun mainMenu() : Int {
    return ScannerInput.readNextInt(
        """ 
         > $ansiCyan--------------------------------------------$ansiReset
         > |       $ansiRed Video Game Review System $ansiReset         |
         > | $ansiRed $ansiReset                                        |
         > $ansiCyan--------------------------------------------$ansiReset
         > | $ansiRed Company Menu $ansiReset                           |
         > |  $ansiBlue 1) Company related inputs $ansiReset             |
         > $ansiCyan--------------------------------------------$ansiReset
         > | $ansiRed Video Game Menu $ansiReset                        |
         > |  $ansiBlue 2) Video related related inputs $ansiReset       |
         > $ansiCyan--------------------------------------------$ansiReset
         > | $ansiRed Company & Video Game Listing Statistics $ansiReset|
         > |  $ansiBlue 3) List Statistics $ansiReset                    |
         > $ansiCyan--------------------------------------------$ansiReset
         > |  $ansiBlue 20) Save Current Data$ansiReset                  |
         > |  $ansiBlue 21) Load Existing Data$ansiReset                 |
         > $ansiCyan--------------------------------------------$ansiReset
         > |  $ansiYellow 0) Exit App $ansiReset                           |
         > $ansiCyan--------------------------------------------$ansiReset
         > $ansiGreen==>> """.trimMargin(">")
    )
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> listCompanyMenu()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun listCompanyMenu() {
        val option = ScannerInput.readNextInt(
            """
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiRed No. of companies: $ansiReset                       |
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   1) Create new company   $ansiReset               |
                  > |$ansiBlue   2) Update existing company $ansiReset            |
                  > |$ansiBlue   3) Delete existing company $ansiReset            |
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   4) Back $ansiReset                               |
                  > $ansiCyan--------------------------------------------$ansiReset
         >$ansiGreen ==>> """.trimMargin(">")
        )

        when (option) {
            4 -> mainMenu()
            else -> println("Invalid option entered: $option")
        }
}

fun main(args: Array<String>){
    runMenu()
}