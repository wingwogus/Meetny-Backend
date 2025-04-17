import React, { memo } from "react";
import { Component } from "./Component";
import { MdiPencil } from "./MdiPencil";
import { Vector } from "./Vector";
import arrow6 from "./arrow-6.svg";
import ellipse4 from "./ellipse-4.png";
import ellipse11 from "./ellipse-11.svg";
import image12 from "./image-12.png";
import image from "./image.svg";
import line31 from "./line-31.svg";
import line32 from "./line-32.svg";
import polygon1 from "./polygon-1.svg";
import "./mypage.css";
import vector2 from "./vector-2.svg";
import vector3 from "./vector-3.svg";
import vector4 from "./vector-4.svg";
import vector5 from "./vector-5.svg";
import vector6 from "./vector-6.svg";
import vector7 from "./vector-7.svg";
import vector8 from "./vector-8.svg";
import vector9 from "./vector-9.svg";
import vector10 from "./vector-10.svg";
import vector11 from "./vector-11.svg";
import vector12 from "./vector-12.svg";
import vector13 from "./vector-13.svg";
import vector14 from "./vector-14.svg";
import vector15 from "./vector-15.svg";

const MyPageComponent = () => {
    return (
        <div className="my-page">
            <div className="content">
                <div className="profile">
                    <div className="overlap">
                        <img className="ellipse" alt="User profile background" src={ellipse4} />

                        <img className="img" alt="Profile image" src={ellipse11} />

                        <MdiPencil className="mdi-pencil" aria-label="프로필 편집 아이콘" />
                    </div>

                    <div className="overlap-group">
                        <div className="text-wrapper">경르민</div>

                        <div className="rectangle" />

                        <div className="div" />

                        <div className="rectangle-2" />

                        <div className="text-wrapper-2">Concert</div>

                        <div className="text-wrapper-3">J-POP</div>

                        <div className="text-wrapper-4">Soccer</div>
                    </div>

                    <Component className="component-5" />
                    <div className="overlap-2">
                        <div className="overlap-3">
                            <div className="rectangle-3" />

                            <div className="rectangle-4" />

                            <Vector className="vector-instance" />
                        </div>

                        <img className="arrow" alt="Arrow pointing direction" src={arrow6} />
                    </div>

                    <div className="text-wrapper-5">72%</div>

                    <div className="text-wrapper-6">매너 배터리</div>

                    <div className="overlap-4">
                        <div className="rectangle-5" />

                        <div className="text-wrapper-7">Basket</div>
                    </div>

                    <div className="div-wrapper">
                        <div className="text-wrapper-8">Indi</div>
                    </div>

                    <div className="text-wrapper-9">첫 충전율 50%</div>

                    <div className="text-wrapper-10">동행 횟수</div>

                    <div className="text-wrapper-11">팔로워</div>

                    <div className="text-wrapper-12">팔로잉</div>

                    <div className="text-wrapper-13">41</div>

                    <div className="text-wrapper-14">16</div>

                    <div className="text-wrapper-15">6</div>

                    <div className="overlap-5">
                        <div className="rectangle-6" />

                        <img className="polygon" alt="Polygon shape" src={polygon1} />

                        <p className="p">
                            매너 배터리는 동행자들에게 받은 칭찬, 후기, 신고 및 차단 내역 등을
                            총합해서 나타내는 신뢰도 수치에요
                        </p>
                    </div>
                </div>

                <div className="history">
                    <div className="text-wrapper-16">구인글(8)</div>

                    <div className="text-wrapper-17">동행 후기(17)</div>

                    <div className="overlap-6">
                        <img className="line" alt="Line separator" src={line32} />

                        <img className="line-2" alt="Line separator" src={line31} />
                    </div>

                    <div className="overlap-7">
                        <div className="overlap-8">
                            <div className="overlap-group-2">
                                <div className="text-wrapper-18">아이묭 콘서트 동행</div>

                                <div className="element">
                                    상암 ~~홀
                                    <br />
                                    <br />
                                    2024.01.01
                                </div>

                                <img className="vector-2" alt="Concert image" src={image} />
                            </div>

                            <img className="vector-3" alt="Vector image" src={vector} />
                        </div>

                        <div className="overlap-9">
                            <div className="rectangle-7" />

                            <div className="text-wrapper-19">Concert</div>
                        </div>
                    </div>

                    <div className="overlap-10">
                        <div className="overlap-8">
                            <div className="overlap-group-2">
                                <div className="text-wrapper-20">아이묭 콘서트 동행</div>

                                <div className="element-2">
                                    상암 ~~홀
                                    <br />
                                    <br />
                                    2024.01.01
                                </div>

                                <img className="vector-2" alt="Concert image" src={vector12} />
                            </div>

                            <img className="vector-3" alt="Vector image" src={vector8} />
                        </div>

                        <div className="overlap-9">
                            <div className="rectangle-8" />

                            <div className="text-wrapper-21">Concert</div>
                        </div>
                    </div>

                    <div className="overlap-11">
                        <div className="overlap-8">
                            <div className="overlap-group-2">
                                <div className="text-wrapper-20">아이묭 콘서트 동행</div>

                                <div className="element-2">
                                    상암 ~~홀
                                    <br />
                                    <br />
                                    2024.01.01
                                </div>

                                <img className="vector-2" alt="Concert image" src={vector6} />
                            </div>

                            <img className="vector-3" alt="Vector image" src={vector4} />
                        </div>

                        <div className="overlap-9">
                            <div className="rectangle-8" />

                            <div className="text-wrapper-21">Concert</div>
                        </div>
                    </div>

                    <div className="overlap-12">
                        <div className="overlap-8">
                            <div className="overlap-group-2">
                                <div className="text-wrapper-20">아이묭 콘서트 동행</div>

                                <div className="element-2">
                                    상암 ~~홀
                                    <br />
                                    <br />
                                    2024.01.01
                                </div>

                                <img className="vector-2" alt="Concert image" src={vector13} />
                            </div>

                            <img className="vector-3" alt="Vector image" src={vector9} />
                        </div>

                        <div className="overlap-9">
                            <div className="rectangle-8" />

                            <div className="text-wrapper-21">Concert</div>
                        </div>
                    </div>

                    <div className="overlap-13">
                        <div className="overlap-8">
                            <div className="overlap-group-2">
                                <div className="text-wrapper-20">아이묭 콘서트 동행</div>

                                <div className="element-2">
                                    상암 ~~홀
                                    <br />
                                    <br />
                                    2024.01.01
                                </div>

                                <img className="vector-2" alt="Concert image" src={vector3} />
                            </div>

                            <img className="vector-3" alt="Vector image" src={vector2} />
                        </div>

                        <div className="overlap-9">
                            <div className="rectangle-8" />

                            <div className="text-wrapper-21">Concert</div>
                        </div>
                    </div>

                    <div className="overlap-14">
                        <div className="overlap-8">
                            <div className="overlap-group-2">
                                <div className="text-wrapper-20">아이묭 콘서트 동행</div>

                                <div className="element-2">
                                    상암 ~~홀
                                    <br />
                                    <br />
                                    2024.01.01
                                </div>

                                <img className="vector-2" alt="Concert image" src={vector14} />
                            </div>

                            <img className="vector-3" alt="Vector image" src={vector10} />
                        </div>

                        <div className="overlap-9">
                            <div className="rectangle-8" />

                            <div className="text-wrapper-21">Concert</div>
                        </div>
                    </div>

                    <div className="overlap-15">
                        <div className="overlap-8">
                            <div className="overlap-group-2">
                                <div className="text-wrapper-20">아이묭 콘서트 동행</div>

                                <div className="element-2">
                                    상암 ~~홀
                                    <br />
                                    <br />
                                    2024.01.01
                                </div>

                                <img className="vector-2" alt="Concert image" src={vector7} />
                            </div>

                            <img className="vector-3" alt="Vector image" src={vector5} />
                        </div>

                        <div className="overlap-9">
                            <div className="rectangle-8" />

                            <div className="text-wrapper-21">Concert</div>
                        </div>
                    </div>

                    <div className="overlap-16">
                        <div className="overlap-8">
                            <div className="overlap-group-2">
                                <div className="text-wrapper-20">아이묭 콘서트 동행</div>

                                <div className="element-2">
                                    상암 ~~홀
                                    <br />
                                    <br />
                                    2024.01.01
                                </div>

                                <img className="vector-2" alt="Concert image" src={vector15} />
                            </div>

                            <img className="vector-3" alt="Vector image" src={vector11} />
                        </div>

                        <div className="overlap-9">
                            <div className="rectangle-8" />

                            <div className="text-wrapper-21">Concert</div>
                        </div>
                    </div>
                </div>
            </div>

            <header className="header">
                <img className="image" alt="Header image" src={image12} />
            </header>
        </div>
    );
};

export const MyPage = memo(MyPageComponent);
