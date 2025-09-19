package br.com.usinasantafe.ppc.utils

enum class StatusSend { STARTED, SEND, SENT }
enum class Errors { FIELD_EMPTY, TOKEN, UPDATE, EXCEPTION, INVALID }
enum class TypeButton { NUMERIC, CLEAN, OK, UPDATE }
enum class FlagUpdate { OUTDATED, UPDATED }
enum class Status { OPEN, CLOSE, FINISH }
enum class StatusCon { WITHOUT, SLOW, OK }
enum class TypeStateSampleList { FINISH, DELETE_ALL, DELETE_ITEM }

enum class LevelUpdate { RECOVERY, CLEAN, SAVE, GET_TOKEN, SAVE_TOKEN, FINISH_UPDATE_INITIAL, FINISH_UPDATE_COMPLETED, CHECK }
enum class Field { TARE, STALK, WHOLE_CANE, STUMP, PIECE, TIP, SLIVERS }

