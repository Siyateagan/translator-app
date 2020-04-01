package siyateagan.example.translatorapp.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelablePair<A, B> (val pair: Pair<A, B>) : Parcelable