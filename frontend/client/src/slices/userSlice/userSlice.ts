import { createSlice } from '@reduxjs/toolkit';
// import { persistReducer } from 'redux-persist';

const initialState = {
  token: localStorage.getItem('accessToken'),
  isAuthenticated: false
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setToken(state, action) {
      state.token = action.payload;
    },
    setIsAuthenticated(state, action) {
      state.isAuthenticated = action.payload.isAuthenticated;
    },
    logoutUser(state) {
      state.isAuthenticated = false;
    }
  }
});

export const { setToken, setIsAuthenticated } = userSlice.actions;

export default userSlice.reducer;
