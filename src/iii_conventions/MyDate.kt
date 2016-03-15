package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        val intermediate = (year * 12 + month) - (other.year * 12 + other.month)
        if (intermediate != 0) {
            return intermediate
        } else {
            return dayOfMonth - other.dayOfMonth
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(toAdd: TimeInterval) = this.plus(RepeatedTimeInterval(toAdd, 1))

operator fun MyDate.plus(toAdd: RepeatedTimeInterval) = addTimeIntervals(toAdd.ti, toAdd.n)

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

operator fun TimeInterval.times(toMult: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, toMult)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start

            override fun hasNext() = current <= endInclusive

            override fun next(): MyDate {
                val ret = current
                current = current.nextDay()
                return ret
            }
        }
    }
}
