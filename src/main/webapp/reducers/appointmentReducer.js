import * as ACTION_TYPES from '../actions/types'

const initialState = {
  list: [],
  error: {}
}

const appointmentReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.APPOINTMENTS_FETCH_ALL:
      return { ...state, list: [...action.payload] }

    case ACTION_TYPES.APPOINTMENTS_ERROR:
      return { ...state, error: action.payload }

    default:
      return state
  }
}

export default appointmentReducer


