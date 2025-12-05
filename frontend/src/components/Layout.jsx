import { Outlet } from "react-router-dom";
import Navbar from "./Navbar.jsx";

export default function Layout() {
  return (
    <>
      <Navbar />
      <div className="pt-24 px-8">
        <Outlet />
      </div>
    </>
  );
}
