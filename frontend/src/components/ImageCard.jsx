export default function ImageCard({ image }) {
  return (
    <div className="w-full">
      <img
        src={image.previewUrl || image.originalUrl}
        alt={image.title}
        className="w-full h-auto object-cover"
      />
      <div className="p-3 text-center text-white font-semibold">
        {image.title}
      </div>
    </div>
  );
}