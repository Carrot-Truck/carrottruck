import { configureStore } from '@reduxjs/toolkit';
import { persistStore } from 'redux-persist';

import userReducer from '../slices/userSlice/userSlice';

export const store: any = configureStore({
  reducer: {
    user: userReducer
  },
  middleware: (geDefaultMiddleware) =>
    geDefaultMiddleware({
      serializableCheck: false
    })
});

export const persistor = persistStore(store);
