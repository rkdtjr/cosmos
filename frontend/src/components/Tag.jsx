export default function Tag({ tag }) {
  return (
    <span
      className="
        px-2 py-1 
        bg-cyan-800/40 
        text-cyan-300 
        border border-cyan-700 
        rounded-md 
        text-xs 
        font-medium
        select-none
        transition
      "
    >
      #{tag}
    </span>
  );
}
