import { useQuery } from "@tanstack/react-query";
import { getAPOD, getRandomGalleryImage, getGallerySearch } from "../api/api";
import { useState } from "react";
import Tag from "../components/Tag"
import ImageCard from "../components/ImageCard";
import ImageDetail from "../components/ImageDetail";

function Gallery() {
  const [keyword, setKeyword] = useState("");
  const [query, setQuery] = useState("");
  const [selectedImage, setSelectedImage] = useState(null);

  const { data, isLoading, isError, refetch } = useQuery({
    queryKey: ["gallery", query],
    queryFn: () =>
      query.trim() === ""
        ? getRandomGalleryImage()
        : getGallerySearch(query),
    refetchOnWindowFocus: false,
  });

  const handleSearch = () => {
    setQuery(keyword);
    refetch();
  };

  if (isLoading) return <p className="text-center mt-10">Loading...</p>;
  if (isError) return <p className="text-center mt-10">오류 발생</p>;

  return (
    <>
      <div>
        <div className="w-full flex justify-center mb-10 mt-4">
          <div className="flex gap-3 w-full max-w-[500px]">
            <input
              type="text"
              placeholder="Search space images..."
              className="flex-1 px-4 py-2 rounded-lg bg-[#0d1b2a] border border-gray-600 text-white outline-none focus:border-cyan-400 transition"
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && handleSearch()}
            />
            <button
              onClick={handleSearch}
              className="px-4 py-2 bg-cyan-600 hover:bg-cyan-500 text-white rounded-lg font-semibold transition"
            >
              검색
            </button>
          </div>
        </div>

        <div className="columns-1 sm:columns-2 md:columns-3 lg:columns-4 xl:columns-5 gap-6 px-6 mx-auto max-w-[1400px]">
          {data?.map(image => (
            <div
              key={image.nasaId}
              className="mb-6 break-inside-avoid rounded-xl bg-[#0d1b2a] shadow-md overflow-hidden cursor-pointer"
              onClick={() => setSelectedImage(image)}
            >
              <ImageCard image={image} />
              <div className="flex gap-2 flex-wrap justify-center px-2 pb-3 mt-2">
                {image.tags?.map(tag => (
                  <Tag key={tag} tag={tag} />
                ))}
              </div>
            </div>
          ))}
        </div>
        <ImageDetail image={selectedImage} onClose={() => setSelectedImage(null)} />
      </div>
    </>
  );

}

export default Gallery;
