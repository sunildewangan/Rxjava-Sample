package com.test.rxjavasample.observers.model

data class Note(val id: Int, val note: String) {

    override fun equals(other: Any?): Boolean {
        super.equals(other)
        if (other === this) {
            return true
        }

        return if (other !is Note) {
            false
        } else note.equals(
            (other as Note).note,
            ignoreCase = true
        )
    }

    override fun hashCode(): Int {
        var hash = 3
        hash = 53 * hash + if (note != null) note.hashCode() else 0
        return hash
    }
}