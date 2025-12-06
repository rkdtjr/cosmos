export default function ImageDetail({ image, onClose }) {
  if (!image) return null;

  return (
    <div
      className="fixed inset-0 bg-black/70 backdrop-blur-sm flex items-center justify-center z-50"
      onClick={onClose}
    >
      <div
        className="bg-[#0d1b2a] rounded-xl shadow-xl p-4 max-w-5xl w-[90%] max-h-[90%] overflow-y-auto"
        onClick={(e) => e.stopPropagation()}
      >
        <img
          src={image.originalUrl || image.previewUrl}
          alt={image.title}
          className="w-full h-auto rounded-lg mb-4"
        />

        <h2 className="text-2xl font-bold text-cyan-300 mb-2 text-center">
          {image.title}
        </h2>

        <p className="text-gray-300 mb-4 text-sm leading-relaxed">
          {image.description}
        </p>

        <div className="flex gap-2 flex-wrap justify-center mb-4">
          {image.tags?.map(tag => (
            <span
              key={tag}
              className="px-2 py-1 bg-cyan-800/40 text-cyan-300 border border-cyan-700 rounded-md text-xs"
            >
              #{tag}
            </span>
          ))}
        </div>

        <button
          onClick={onClose}
          className="block mx-auto px-4 py-2 bg-cyan-600 hover:bg-cyan-500 text-white rounded-lg font-semibold transition"
        >
          닫기
        </button>
      </div>
    </div>
  );
}
