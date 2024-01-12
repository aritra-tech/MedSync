package com.aritra.medsync.domain.model

enum class MedicineType {
    TABLET, CAPSULE, SYRUP, INHALER;

    companion object {
        fun getMedicineTypeString(medicineType: MedicineType): String {
            return when(medicineType) {
                TABLET -> "TABLET"
                CAPSULE -> "CAPSULE"
                SYRUP -> "SYRUP"
                INHALER -> "INHALER"
            }
        }
    }
}