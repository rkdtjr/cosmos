export default function ImageCard({ image }) {
  return (
    <div>
      <img src={image.previewUrl} width={250} />
      <h3>{image.title}</h3>
      <p>{image.description}</p>
    </div>
  );
}