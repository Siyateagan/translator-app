package siyateagan.example.translatorapp.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

/**
 * Represents parcelable pair @see [Pair]
 */
@Parcelize
data class ParcelablePair<A, B>(
    val first: @RawValue A,
    val second: @RawValue B
) : Parcelable {

    override fun toString(): String = "($first, $second)"
    fun toPair(): Pair<A, B> = Pair(first, second)
}

