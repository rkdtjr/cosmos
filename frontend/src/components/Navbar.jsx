import { Link, NavLink } from "react-router-dom";

export default function Navbar() {
  return (
    <nav
      className="fixed top-0 left-0 w-full px-8 py-4 text-white flex items-center z-50"
      style={{
        background: "linear-gradient(90deg, #05070F, #0D2B55, #041020)"
      }}

    >
      <div className="font-bold text-2xl text-[#009bb0]">
        <Link to="/">
          Cosmos</Link>
      </div>

      <div className="flex gap-10 ml-auto text-lg">
        <NavLink
          to="/"
          className={({ isActive }) =>
            `relative pb-1 transition ${isActive ? "text-cyan-300 font-semibold" : "text-gray-300"
            }`
          }
        >
          Home
        </NavLink>

        <NavLink
          to="/gallery"
          className={({ isActive }) =>
            `relative pb-1 transition ${isActive ? "text-cyan-300 font-semibold" : "text-gray-300"
            }`
          }
        >
          Gallery
        </NavLink>

        <NavLink
          to="/dictionary"
          className={({ isActive }) =>
            `relative pb-1 transition ${isActive ? "text-cyan-300 font-semibold" : "text-gray-300"
            }`
          }
        >
          Dictionary
        </NavLink>
      </div>
    </nav>
  );
}
