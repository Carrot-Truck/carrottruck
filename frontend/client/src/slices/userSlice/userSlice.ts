import { createSlice } from '@reduxjs/toolkit';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

const initialState = {
  token: localStorage.getItem('accessToken'),
  isAuthenticated: false
};

const persistConfig = {
  key: 'user',
  storage: storage,
  whitelist: ['token', 'isAuthenticated']
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setToken(state, action) {
      state.token = action.payload;
    },
    setIsAuthenticated(state, action) {
      state.isAuthenticated = action.payload;
    },
    logoutUser(state) {
      state.isAuthenticated = false;
    }
  }
});

export const { setToken, setIsAuthenticated } = userSlice.actions;

export default persistReducer(persistConfig, userSlice.reducer);
