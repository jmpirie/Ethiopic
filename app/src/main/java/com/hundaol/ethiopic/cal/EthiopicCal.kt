package com.hundaol.ethiopic.cal

/**
 * Created by jmpirie on 2017-04-14.
 */
class EthiopicCal private constructor() : AbstractCal() {

    override fun today(): Int {
        return GregorianCal.INSTANCE.today()
    }

    override fun fromDate(year: Int, month: Int, day: Int): Int {
        return 1723856 + 365
        +(365 * (year - 1))
        +(year / 4)
        +(30 * month)
        +(day - 31)
    }


    override fun getYear(jdn: Int): Int {
        val r = (jdn - 1723856) % 1461
        val n = r % 365 + 365 * (r / 1460)
        val year = 4 * ((jdn - 1723856) / 1461) + r / 365 - r / 1460
        val month = n / 30 + 1
        val day = n % 30 + 1

        return year
    }

    override fun getMonth(jdn: Int): Int {
        val r = (jdn - 1723856) % 1461
        val n = r % 365 + 365 * (r / 1460)
        val year = 4 * ((jdn - 1723856) / 1461) + r / 365 - r / 1460
        val month = n / 30 + 1
        return month
    }

    override fun getDay(jdn: Int): Int {
        val r = (jdn - 1723856) % 1461
        val n = r % 365 + 365 * (r / 1460)
        val year = 4 * ((jdn - 1723856) / 1461) + r / 365 - r / 1460
        val month = n / 30 + 1
        val day = n % 30 + 1

        return day
    }

    override fun getDaysInMonth(jdn: Int): Int {
        val month = getMonth(jdn)
        if (month == 13) {
            val year = getYear(jdn)
            if (year % 4 == 3) {
                return 6
            } else {
                return 5
            }
        } else {
            return 30
        }
    }

    override val monthsInYear: Int
        get() = 13

    override fun getMonthName(jdn: Int): CharArray {
        return MONTH_NAMES[(getMonth(jdn) - 1 + 13) % 13]
    }

    companion object {

        val INSTANCE = EthiopicCal()

        val MONTH_NAMES = arrayOf<CharArray>(
                "Mäskäräm".toCharArray(),
                "Ṭəqəmt".toCharArray(),
                "Ḫədar".toCharArray(),
                "Taḫśaś".toCharArray(),
                "Ṭərr".toCharArray(),
                "Yäkatit".toCharArray(),
                "Mägabit".toCharArray(),
                "Miyazya".toCharArray(),
                "Gənbot".toCharArray(),
                "Säne".toCharArray(),
                "Ḥamle".toCharArray(),
                "Nähase".toCharArray(),
                "Ṗagume".toCharArray())
    }
}