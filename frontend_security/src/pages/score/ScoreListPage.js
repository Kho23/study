import React from 'react'
import ScoreListComponent from '../../components/score/ScoreListComponent'

const ScoreListPage = () => {
  return (
    <div className="p-4 w-full bg-orange-200">
      <div className="text-3xl font-extrabold">
        Score List Page components 
      </div>
      <ScoreListComponent />
    </div>
  )
}

export default ScoreListPage