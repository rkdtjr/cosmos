import { Routes, Route } from "react-router-dom";
import Layout from "./layout/Layout";
import Home from "./pages/Home";
import Gallery from "./pages/Gallery"
import Dictionary from "./pages/Dictionary"

export default function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path="/" element={<Home />} />
        <Route path="/gallery" element={<Gallery />} />
        <Route path="/dictionary" element={<Dictionary />} />
      </Route>
    </Routes>
  );
}
