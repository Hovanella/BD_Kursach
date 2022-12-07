import './App.css';
import React from 'react';
import { Routes, Route } from "react-router-dom";
import RegisterPage from "./Pages/RegisterPage/RegisterPage";
import HomePage from "./Pages/HomePage/HomePage";
import {AdminPage} from "./Pages/AdminPage/AdminPage";
import LoginPage from "./Pages/LoginPage/LoginPage";
import { StatisticsPage } from "./Pages/StatisticsPage/StatisticPage";
import { PlaylistsPage } from "./Pages/PlaylistsPage/PlaylistsPage";

function App() {
  return (
      <div className="pageContainer">
        <Routes>
          <Route path="/" element={<RegisterPage/>} />
          <Route path="/statistics" element={<StatisticsPage/>} />
          <Route path="/home" element={<HomePage/>} />
          <Route path="/admin" element={<AdminPage/>} />
          <Route path="/playlists" element={<PlaylistsPage/>} />
          <Route path="/login" element={<LoginPage/>} />
          <Route path="/register" element={<RegisterPage/>} />
        </Routes>
      </div>
  );
}

export default App;
