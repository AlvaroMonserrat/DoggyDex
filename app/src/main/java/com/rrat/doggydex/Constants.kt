package com.rrat.doggydex

//API
const val BASE_URL = "https://todogs.herokuapp.com/api/v1/"
const val GET_ALL_DOGS = "dogs"
const val SIGN_UP = "sign_up"
const val SIGN_IN = "sign_in"
const val ADD_DOG_TO_USER = "add_dog_to_user"
const val GET_USER_DOGS_URL = "get_user_dogs"
const val GET_DOG_BY_ML_ID = "find_dog_by_ml_id"

//TENSOR FLOW
const val MAX_RECOGNITION_DOG_RESULTS = 5
const val MODEL_PATH = "model.tflite"
const val LABEL_PATH = "labels.txt"

//EXTRAS
const val DOG_EXTRA = "dog"
const val PHOTO_URI_EXTRA = "photo_uri"
const val MOST_PROBABLE_DOGS_IDS_EXTRA = "most_probable_dogs_ids"
const val IS_RECOGNITION = "is_recognition"

//ERROR MESSAGES
const val ERROR_SIGN_UP = "Error Sign up"
const val ERROR_SIGN_IN = "Error Sign in"
const val ERROR_WRONG_USER_OR_PASS = "Wrong User or Password"
const val USER_ALREADY_EXIST = "User already exists"
const val UNKNOWN_ERROR = "Unknown Error"
const val ERROR_ADDING_DOG = "Error Adding Dog"