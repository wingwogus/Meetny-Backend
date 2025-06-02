import React from "react";
import GroupCard from "./GroupCard";
import mask1 from "../assets/mask-group.png";

const dummyGroups = [
    {
        title: "💪축구/풋살 모임 : 풋살즈",
        description: "다함께 으쌰으쌰하며 친목도 다지고...",
        date: "25.05.18",
        place: "연남동",
        memberCount: "11/15",
        image: mask1
    },
    // ...더미 데이터 추가
];

const PopularGroupSection = () => {
    return (
        <section className="popular-groups">
            <h2>우리동네 인기모임</h2>
            <div className="group-grid">
                {dummyGroups.map((g, i) => (
                    <GroupCard key={i} {...g} />
                ))}
            </div>
        </section>
    );
};

export default PopularGroupSection;