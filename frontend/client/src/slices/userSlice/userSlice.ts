import { createSlice } from '@reduxjs/toolkit';
// import { persistReducer } from 'redux-persist';

const initialState = {
  token: sessionStorage.getItem('accessToken'),
  userEmail: '',
  nickname: '',
  phoneNumber: '',
  role: '',
  isAuthenticated: false,
  active: ''
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setToken(state, action) {
      state.token = action.payload;
    },
    setUserEmail(state, action) {
      state.userEmail = action.payload;
    },
    setNickname(state, action) {
      state.nickname = action.payload;
    },
    setPhoneNumber(state, action) {
      state.phoneNumber = action.payload;
    },
    setRole(state, action) {
      state.role = action.payload;
    },
    setIsAuthenticated(state, action) {
      state.isAuthenticated = action.payload;
    },
    setActive(state, action) {
      state.active = action.payload;
    },
    logoutUser(state) {
      state.isAuthenticated = false;
    }
  }
});

export const { setToken, setUserEmail, setNickname, setPhoneNumber, setRole, setActive } = userSlice.actions;

export default userSlice.reducer;
