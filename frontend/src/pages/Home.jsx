import { useQuery } from "@tanstack/react-query";
import ImageCard from "../components/ImageCard";
import { getAPOD } from "../api/api";

function Home() {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['apod'],
    queryFn: () => getAPOD(),
  })

  if (isLoading) {
    return <p className="text-center mt-10">Loading...</p>
  }

  if (isError) {
    return <p className="text-center mt-10">오류 발생: {error.message}</p>
  }

  return (
    <>
      <div className="text-center mt-20">
        <h1 className="text-7xl font-extrabold tracking-wide text-[#00e5ff]">
          Cosmos
        </h1>

        <p className="mt-4 text-gray-300 text-lg max-w-[700px] mx-auto leading-relaxed">
          어쩌고 저쩌고 설명 잔뜩
        </p>
      </div>


      <div className="w-full flex flex-col bg-[#0d1b2a] md:flex-row items-stretch justify-between max-w-[1500px] mx-auto gap-1 mt-10 border border-gray-600 rounded-xl">

        <div className="flex-shrink-0">
          <img
            src={data.hdurl || data.url}
            alt={data.title}
            className="rounded-lg shadow-lg max-h-[600px] object-contain"
          />
        </div>

        <div className="flex flex-col justify-between">
          <h2 className="text-3xl font-bold mb-4">
            {data.title}
          </h2>
          <p className="text-gray-300 leading-relaxed text-lg">
            {data.explanation}
          </p>
          <p className="text-gray-500 leading-relaxed test-md">
            copyright : {data.copyright}
          </p>
        </div>

      </div>


    </>
  );
}

export default Home;
